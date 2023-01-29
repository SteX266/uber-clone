import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehicle } from 'src/app/models/car';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';
import { Review } from 'src/app/models/Review';
import { Image } from 'src/app/models/Image';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly changePasswordUrl: string;
  private readonly createReviewUrl: string;
  private readonly addImageUrl:string;

  addImage(image: Image) {
    return this.http.post<any>(this.addImageUrl, image, this.authService.getHttpOptionsWithToken());
  }
  updateUser(user: UserProfileInfo) {
    return this.http.post<any>(
      environment.apiEndpoint + 'profile/updateUserProfileInfo',
      user,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getUserById(idNumber: number): UserProfileInfo {
    let user = new UserProfileInfo(0, '', '', '', '', '', '', '');

    this.sendGetUserRequest(idNumber).subscribe({
      next: (val) => {
        user.city = val.city;
        user.email = val.email;
        user.id = val.id;
        user.name = val.name;
        user.surname = val.surname;
        user.phoneNumber = val.phoneNumber;
        user.profilePicture = val.profilePicture;
        user.role = val.role;
      },
    });
    return user;
  }

  sendGetUserRequest(id: number) {
    return this.http.get<UserProfileInfo>(
      environment.apiEndpoint + 'profile/getProfileInfo/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  sendChangePasswordRequest(
    id: string,
    oldPassword: string,
    newPassword: string
  ) {
    let body = {
      userId: id,
      oldPassword: oldPassword,
      newPassword: newPassword,
    };
    return this.http.post<any>(
      this.changePasswordUrl,
      body,
      this.authService.getHttpOptionsWithToken()
    );
  }

  createReview(review: Review) {
    let body = {
      rideId: review.rideId,
      driverRating: review.driverRating,
      vehicleRating: review.vehicleRating,
      comment: review.comment,
      reviewerEmail: this.authService.getCurrentUserEmail(),
    };
    return this.http.post<any>(
      this.createReviewUrl,
      body,
      this.authService.getHttpOptionsWithToken()
    );
  }
  constructor(private http: HttpClient, private authService: AuthService) {
    this.changePasswordUrl = environment.apiEndpoint + 'profile/changePassword';
    this.createReviewUrl = environment.apiEndpoint + 'review/createReview';
    this.addImageUrl = environment.apiEndpoint + 'profile/addImage';
  }
}
