export class Review{

    rideId:number;
    driverRating:number;
    vehicleRating:number;
    comment:string;

    constructor(rideId:number, driverRating:number,vehicleRating:number, comment:string){
        this.rideId = rideId;
        this.driverRating = driverRating;
        this.vehicleRating = vehicleRating;
        this.comment = comment;

    }




}