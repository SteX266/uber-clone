export class Image{
    data:string;
    path:string;
    userId:string;
    constructor(data:string,path:string, userId:string){
        this.data = data;
        this.path = path;
        this.userId = userId;
    }
}