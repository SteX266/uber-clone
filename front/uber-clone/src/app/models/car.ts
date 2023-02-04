export class Vehicle {
  id!: number;
  model!: string;
  numberOfSeats!: number;
  allowsPet!: boolean;
  allowsBaby!: boolean;
  type!: string;
  driver!: number;

  constructor(
    id: number,
    model: string,
    numberOfSeats: number,
    allowsPet: boolean,
    baby: boolean,
    type: string,
    driver: number
  ) {
    this.id = id;
    this.model = model;
    this.numberOfSeats = numberOfSeats;
    this.allowsPet = allowsPet;
    this.allowsBaby = baby;
    this.type = type;
    this.driver = driver;
  }
}
