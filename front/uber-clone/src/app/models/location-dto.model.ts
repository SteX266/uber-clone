export class LocationDTO {
  driverId: number;
  latitude: number;
  longitude: number;
  constructor(driverId: number, latitude: number, longitude: number) {
    this.driverId = driverId;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
