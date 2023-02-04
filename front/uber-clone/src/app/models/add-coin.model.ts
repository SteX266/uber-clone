export class AddCoin{
    email:string;
    coinAmount:number;

    constructor(email:string,coinAmount:number){
        this.email = email;
        this.coinAmount = coinAmount;
    }
}