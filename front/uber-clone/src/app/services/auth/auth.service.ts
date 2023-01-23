import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserCredentials } from 'src/app/models/UserCredentials';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { SocialUser } from '@abacritt/angularx-social-login';
import { UserRegistrationRequest } from 'src/app/models/UserRegistrationRequest';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly loginUrl: string;
  private readonly signUpUrl: string;
  private readonly loginSocialUrl:string;
  private readonly registrationUrl:string;
  constructor(private http: HttpClient) {
      this.loginUrl = environment.apiEndpoint + 'auth/login';
      this.signUpUrl = environment.apiEndpoint + 'auth/usersignup';
      this.loginSocialUrl = environment.apiEndpoint + 'auth/loginSocial';
      this.registrationUrl = environment.apiEndpoint + "auth/usersignup";

    
   }

  public saveToken(token: string):void{
    try{
      localStorage.setItem('token', token);
    }
    catch(err){
      console.error(err);
    }
  }
  public getToken():string {
    try {
      const token = localStorage.getItem('token');
      if (token === null) {
        return '';
      }
      return token;
    } catch (err) {
      return '';
    }
  }
  
  invalidateToken(): void{
    localStorage.removeItem('token');
  }

  logout(){
    this.invalidateToken();
  }

  getHttpOptions(){
    return {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Content-Type':  'application/json',
      })
    };

  }

  public getHttpOptionsWithToken(){
    return {
      headers: new HttpHeaders({
        'Access-Control-Allow-Origin': '*',
        'Authorization': this.getToken(),
        'Content-Type': 'application/json',
      })
    };
  }

  sendLoginRequest(credentials:UserCredentials): Observable<any> {
    let body = {
      email: credentials.email,
      password: credentials.password,
    };
    return this.http.post<any>(this.loginUrl, body, this.getHttpOptions());
  }

  socialLogin(user:SocialUser): Observable<any>{
    let body = {
      email: user.email,
      firstName: user.firstName,
      id: user.id,
      lastName: user.lastName,
      name: user.name,
      photoUrl: user.photoUrl,
      provider: user.provider,
    };
    return this.http.post<any>(this.loginSocialUrl, body, this.getHttpOptions());

  }

  sendRegistrationRequest(registrationRequest:UserRegistrationRequest):Observable<any>{
    let body = {
      "email":registrationRequest.email,
      "password":registrationRequest.password,
      "name":registrationRequest.name,
      "surname":registrationRequest.surname,
      "city":registrationRequest.city,
      "phoneNumber":registrationRequest.phoneNumber,
      "userType":registrationRequest.userType
    }
    console.log(body);

    return this.http.post<any>(this.registrationUrl,body,this.getHttpOptions());

  }



}
