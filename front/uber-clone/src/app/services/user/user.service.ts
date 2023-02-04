import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehicle } from 'src/app/models/car';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';
import { Review } from 'src/app/models/Review';
import { Image } from 'src/app/models/Image';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { AddCoin } from 'src/app/models/add-coin.model';
import { Report } from 'src/app/models/report';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly changePasswordUrl: string;
  private readonly createReviewUrl: string;
  private readonly getCoinAmountUrl: string;
  private readonly addImageUrl: string;
  private readonly addCoinsUrl: string;

  srcData: SafeResourceUrl | undefined;

  addImage(image: Image) {
    return this.http.post<any>(
      this.addImageUrl,
      image,
      this.authService.getHttpOptionsWithBlob()
    );
  }
  updateUser(user: UserProfileInfo) {
    return this.http.post<any>(
      environment.apiEndpoint + 'profile/updateUserProfileInfo',
      user,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getUserById(idNumber: number): UserProfileInfo {
    let user = new UserProfileInfo(0, '', '', '', '', '', '', '', 0, 0);

    this.sendGetUserRequest(idNumber).subscribe({
      next: (val) => {
        user.city = val.city;
        user.email = val.email;
        user.id = val.id;
        user.name = val.name;
        user.surname = val.surname;
        user.phoneNumber = val.phoneNumber;
        user.role = val.role;
        user.driverScore = val.driverScore;
        user.vehicleScore = val.vehicleScore;
        console.log(user);
      },
    });
    return user;
  }

  getImage(id: number) {
    return this.http.get<any>(
      environment.apiEndpoint + 'profile/getImage/' + id,
      this.authService.getHttpOptionsWithBlob()
    );
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

  isReviewPossible(id: number) {
    let email = this.authService.getCurrentUserEmail();
    let body = {
      rideId: id,
      reviewerEmail: email,
    };

    return this.http.post<any>(
      environment.apiEndpoint + 'review/reviewPossible',
      body,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getCurrentUserCoinAmount(): number {
    let coins = 0;
    this.sendGetCurrentUserCoinAmountRequest().subscribe({
      next: (val) => {
        coins = val;
      },
      error: (err) => {},
    });
    return coins;
  }

  sendGetCurrentUserCoinAmountRequest() {
    return this.http.get<number>(
      this.getCoinAmountUrl,
      this.authService.getHttpOptionsWithToken()
    );
  }

  addCoins(coins: number) {
    let coinDTO = new AddCoin(this.authService.getCurrentUserEmail(), coins);
    return this.http.post(
      this.addCoinsUrl,
      coinDTO,
      this.authService.getHttpOptionsWithToken()
    );
  }

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private sanitizer: DomSanitizer
  ) {
    this.changePasswordUrl = environment.apiEndpoint + 'profile/changePassword';
    this.createReviewUrl = environment.apiEndpoint + 'review/createReview';
    this.getCoinAmountUrl =
      environment.apiEndpoint +
      'customer/getCustomerCoinAmount/' +
      authService.getCurrentUserId();
    this.addImageUrl = environment.apiEndpoint + 'profile/addImage';
    this.addCoinsUrl = environment.apiEndpoint + 'customer/addCoins';
  }

  banUser(id: Number) {
    return this.http.get<UserProfileInfo>(
      environment.apiEndpoint + 'profile/banUser/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getUserReports(dateFrom: Date, dateTo: Date, id: number) {
    let body = {
      userId: id,
      startDate: dateFrom,
      endDate: dateTo,
      userRole: this.authService.getCurrentUserRole(),
    };
    return this.http.post<Report>(
      environment.apiEndpoint + 'report/getUserReport',
      body,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
