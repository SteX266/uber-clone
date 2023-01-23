export class UserProfileInfo {
  id!: number;
  email!: string;
  name!: string;
  surname!: string;
  city!: string;
  phoneNumber!: string;
  profilePicture!: string;
  role!: string;

  constructor(id:number, email:string,name:string,surname:string,city:string, phoneNumber:string,profilePicture:string,role:string){
    this.id = id;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.city = city;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
    this.role = role;
  }
}
