/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.transport.lwm2m.server;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.leshan.core.californium.LwM2mCoapResource;
import org.thingsboard.server.common.transport.TransportServiceCallback;

@Slf4j
public abstract class AbstractLwm2mTransportResource extends LwM2mCoapResource {
    protected final LwM2mTransportMsgHandler handler;

    public AbstractLwm2mTransportResource(LwM2mTransportMsgHandler handler, String name) {
        super(name);
        this.handler = handler;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        processHandleGet(exchange);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        processHandlePost(exchange);
    }

    protected abstract void processHandleGet(CoapExchange exchange);

    protected abstract void processHandlePost(CoapExchange exchange);

    public static class CoapOkCallback implements TransportServiceCallback<Void> {
        private final CoapExchange exchange;
        private final CoAP.ResponseCode onSuccessResponse;
        private final CoAP.ResponseCode onFailureResponse;

        public CoapOkCallback(CoapExchange exchange, CoAP.ResponseCode onSuccessResponse, CoAP.ResponseCode onFailureResponse) {
            this.exchange = exchange;
            this.onSuccessResponse = onSuccessResponse;
            this.onFailureResponse = onFailureResponse;
        }

        @Override
        public void onSuccess(Void msg) {
            Response response = new Response(onSuccessResponse);
            response.setAcknowledged(isConRequest());
            exchange.respond(response);
        }

        @Override
        public void onError(Throwable e) {
            exchange.respond(onFailureResponse);
        }

        private boolean isConRequest() {
            return exchange.advanced().getRequest().isConfirmable();
        }
    }

    public static class CoapNoOpCallback implements TransportServiceCallback<Void> {
        private final CoapExchange exchange;

        CoapNoOpCallback(CoapExchange exchange) {
            this.exchange = exchange;
        }

        @Override
        public void onSuccess(Void msg) {
        }

        @Override
        public void onError(Throwable e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
