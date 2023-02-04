export class ReservationDTO {
  stops: Array<string>;

  reservationTime: Date;

  routeGeoJson: Array<string>;

  customers: Array<string>;

  vehicleType: string;

  reservationType: string;

  hasBaby: boolean;

  hasPet: boolean;

  distance: number;

  estimatedTime: number;

  estimatedCost: number;
  startCoordinates: number[];
  endCoordinates: number[];

  constructor(
    stops: Array<string> = [],
    reservationTime: Date = new Date(),
    routeGeoJson: Array<string> = [],
    customers: Array<string> = [],
    vehicleType: string = '',
    reservationType: string = '',
    hasBaby: boolean = false,
    hasPet: boolean = false,
    distance: number = 0,
    estimatedTime: number = 0,
    estimatedCost: number = 0,
    startCoordinates: number[] = [],
    endCoordinates: number[] = []
  ) {
    this.stops = stops;
    this.reservationTime = reservationTime;
    this.routeGeoJson = routeGeoJson;
    this.customers = customers;
    this.vehicleType = vehicleType;
    this.reservationType = reservationType;
    this.hasBaby = hasBaby;
    this.hasPet = hasPet;
    this.distance = distance;
    this.estimatedTime = estimatedTime;

    this.estimatedCost = estimatedCost;
    this.startCoordinates = startCoordinates;
    this.endCoordinates = endCoordinates;
  }
}
