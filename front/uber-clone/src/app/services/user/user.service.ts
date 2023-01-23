import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  updateUser(user: UserProfileInfo) {
    console.log(user);
  }
  profileInfoUrl = 'http://localhost:8080/profile/getProfileInfo';
  getUserById(idNumber: number) {

    let user = new UserProfileInfo(0,"","","","","","","");
    
    
    this.sendGetUserRequest(idNumber).subscribe({next:(val)=>{
      console.log(val);
      user.city = val.city;
      user.email = val.email;
      user.id = idNumber;
      user.name = val.name;
      user.surname = val.surname;
      user.phoneNumber = val.phoneNumber;
      user.profilePicture = val.profilePicture;
      user.role = val.role;
    }});

    console.log(user);
    return user;
  }

  sendGetUserRequest(id:number){
    return this.http.get<UserProfileInfo>(environment.apiEndpoint + "profile/getProfileInfo/" + id, this.authService.getHttpOptionsWithToken());



  }
  constructor(private http: HttpClient, private authService:AuthService) {}
}
