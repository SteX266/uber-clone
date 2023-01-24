import { Time } from '@angular/common';
import { UserProfileInfo } from './user-profile-info';

export class Ride {
  id: number;

  customers: UserProfileInfo[];

  vehicleType: string;

  hasBaby: boolean;

  hasPet: boolean;

  type: string;

  status: string;

  estimatedCost: number;

  driver: UserProfileInfo;

  startTime: Date;

  endTime: Date;

  stops: string[];

  constructor(
    id: number,
    customers: UserProfileInfo[],
    vehicleType: string,
    hasBaby: boolean,
    hasPet: boolean,
    type: string,
    status: string,
    estimatedCost: number,
    driver: UserProfileInfo,
    startTime: Date,
    endTime: Date,
    stops: string[]
  ) {
    this.id = id;
    this.customers = customers;
    this.vehicleType = vehicleType;
    this.hasBaby = hasBaby;
    this.hasPet = hasPet;
    this.type = type;
    this.status = status;
    this.estimatedCost = estimatedCost;
    this.driver = driver;
    this.startTime = startTime;
    this.endTime = endTime;
    this.stops = stops;
  }
}
