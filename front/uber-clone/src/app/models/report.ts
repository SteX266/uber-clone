export class Report {
  labels: string[];
  prices: number[];
  numberOfRides: number[];
  distance: number[];

  constructor(
    labels: string[],
    prices: number[],
    numberOfRides: number[],
    distance: number[]
  ) {
    this.labels = labels;
    this.prices = prices;
    this.numberOfRides = numberOfRides;
    this.distance = distance;
  }
}
