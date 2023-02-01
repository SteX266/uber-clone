export class GeoJsonFeatureCollection {
  bbox: number[];
  features: GeoJsonFeature[];
  metadata: Metadata;

  type: string;
  constructor(
    bbox: number[],
    features: GeoJsonFeature[],
    metadata: Metadata,
    type: string
  ) {
    this.bbox = bbox;
    this.features = features;
    this.metadata = metadata;
    this.type = type;
  }
}

export class Query {
  alternative_routes: any;
  coordinates: Location[];
  format: string;
  profile: string;
  constructor(
    alternative_routes: any,
    coordinates: Location[],
    format: string,
    profile: string
  ) {
    this.alternative_routes = alternative_routes;
    this.coordinates = coordinates;
    this.format = format;
    this.profile = profile;
  }
}
export class Metadata {
  attribution: string;
  engine: Engine;
  query: Query;
  service: string;
  timestamp: number;

  constructor(
    attribution: string,
    engine: Engine,
    query: Query,
    service: string,
    timestamp: number
  ) {
    this.attribution = attribution;
    this.engine = engine;
    this.query = query;
    this.service = service;
    this.timestamp = timestamp;
  }
}

export class Engine {
  build_date: string;
  graph_date: string;
  version: string;
  constructor(build_date: string, graph_date: string, version: string) {
    this.build_date = build_date;
    this.graph_date = graph_date;
    this.version = version;
  }
}

export class GeoJsonFeature {
  bbox: number[];
  geometry: Geometry;
  properties: Properties;
  type: string;
  constructor(
    bbox: number[],
    geometry: Geometry,
    properties: Properties,
    type: string
  ) {
    this.bbox = bbox;
    this.geometry = geometry;
    this.properties = properties;
    this.type = type;
  }
}

export class Geometry {
  coordinates: Location[];
  type: string;
  constructor(coordinates: Location[], type: string) {
    this.coordinates = coordinates;
    this.type = type;
  }
}

export class Location {
  latitude: number;
  longitude: number;
  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
export class Properties {
  segments: Segment[];
  summary: Summary;
  way_points: number[];
  constructor(segments: Segment[], summary: Summary, way_points: number[]) {
    this.segments = segments;
    this.summary = summary;
    this.way_points = way_points;
  }
}

export class Segment {
  distance: number;
  duration: number;
  steps: Step[];
  constructor(distance: number, duration: number, steps: Step[]) {
    this.distance = distance;
    this.duration = duration;
    this.steps = steps;
  }
}

export class Step {
  distance: number;
  duration: number;
  type: number;
  instruction: string;
  name: string;
  way_points: number[];
  constructor(
    distance: number,
    duration: number,
    type: number,
    instruction: string,
    name: string,
    way_points: number[]
  ) {
    this.distance = distance;
    this.duration = duration;
    this.type = type;
    this.instruction = instruction;
    this.name = name;
    this.way_points = way_points;
  }
}

export class Summary {
  distance: number;
  duration: number;
  constructor(distance: number, duration: number) {
    this.distance = distance;
    this.duration = duration;
  }
}
