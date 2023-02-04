export class MapPoint {
  name: string;
  latitude: number;
  longitude: number;

  constructor(name: string, latitude: number, longitude: number) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }
  // BITNO JAKO DA PRVO IDE LONG A ONDA LAT
  getCoordinates(): number[] {
    return [this.longitude, this.latitude];
  }

  setCoordinates(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
