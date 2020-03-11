import { LatLngExpression, LatLngTuple } from 'leaflet';

export interface MapOptions {
    initCallback?: Function,
    defaultZoomLevel?: number,
    dontFitMapBounds?: boolean,
    disableScrollZooming?: boolean,
    minZoomLevel?: number,
    latKeyName?: string,
    lngKeyName?: string,
    xPosKeyName?: string,
    yPosKeyName?: string,
    mapProvider: MapProviders,
    mapUrl?: string;
    credentials?: any, // declare credentials format
    defaultCenterPosition?: LatLngTuple,
    markerClusteringSetting?,
    useDefaultCenterPosition?: boolean,
    gmDefaultMapType?: string,
    useLabelFunction: string;
    icon?: any;
}

export enum MapProviders {
    google = 'google-map',
    openstreet = 'openstreet-map',
    here = 'here',
    image = 'image-map',
    tencent = 'tencent-map'
}

export interface MarkerSettings extends MapOptions {
    icon?: any;
    showLabel?: boolean,
    draggable?: boolean,
    displayTooltip?: boolean,
    color?: string,
    currentImage?: string;
    useMarkerImageFunction?: boolean,
    markerImages?: string[],
    markerImageFunction?: Function;
}