export class AbortDTO {
  rideId: number;
  reason: string;
  constructor(rideId: number, reason: string) {
    this.rideId = rideId;
    this.reason = reason;
  }
}
