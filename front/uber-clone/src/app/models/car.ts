export class Vehicle {
  id!: number;
  model!: string;
  numberOfSeats!: number;
  allowsPet!: boolean;
  allowsBaby!: boolean;
  type!: string;

  constructor(
    id: number,
    model: string,
    numberOfSeats: number,
    allowsPet: boolean,
    baby: boolean,
    type: string
  ) {
    this.id = id;
    this.model = model;
    this.numberOfSeats = numberOfSeats;
    this.allowsPet = allowsPet;
    this.allowsBaby = baby;
    this.type = type;
  }
}
