import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfileInfo } from 'src/app/models/user-profile-info';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  updateUser(user: UserProfileInfo) {
    console.log(user);
  }
  profileInfoUrl = 'http://localhost:8080/profile/getProfileInfo';
  getUserById(idNumber: number) {
    let user = new UserProfileInfo();
    user.id = 1;
    user.email = 'vanjas@gmail.com';
    user.name = 'Vanja';
    user.surname = 'Serfeze';
    user.city = 'Zrenjanin';
    user.phoneNumber = '0665241322';
    user.profilePicture =
      'https://upload.wikimedia.org/wikipedia/commons/4/48/Outdoors-man-portrait_%28cropped%29.jpg';
    user.role = 'client';
    return user;
  }

  constructor(private http: HttpClient) {}
}
