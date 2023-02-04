export class AbortDTO {
  rideId: number;
  reason: string;
  driverId: number;
  constructor(rideId: number, driverId: number, reason: string) {
    this.rideId = rideId;
    this.driverId = driverId;
    this.reason = reason;
  }
}
