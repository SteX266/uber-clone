import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfileInfo } from 'src/app/models/user-profile-info';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class UpdateRequestService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllUpdateUserRequest() {
    return this.http.get<UserProfileInfo[]>(
      environment.apiEndpoint + 'updateRequest/getAllUpdateUserRequests',
      this.authService.getHttpOptionsWithToken()
    );
  }

  getUpdateUserRequest(id: Number) {
    return this.http.get<UserProfileInfo>(
      environment.apiEndpoint + 'updateRequest/getUpdateUserRequest/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  getOldUserDataByRequestId(id: Number) {
    return this.http.get<UserProfileInfo>(
      environment.apiEndpoint + 'updateRequest/getOldUserDataByRequestId/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  acceptRequest(id: Number) {
    return this.http.get<boolean>(
      environment.apiEndpoint + 'updateRequest/acceptRequest/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  declineRequest(id: Number) {
    return this.http.get<boolean>(
      environment.apiEndpoint + 'updateRequest/declineRequest/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
