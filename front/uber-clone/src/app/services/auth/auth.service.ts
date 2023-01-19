import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserCredentials } from 'src/app/models/UserCredentials';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly loginUrl: string;
  private readonly signUpUrl: string;
  constructor(private http: HttpClient) {
      this.loginUrl = environment.apiEndpoint + 'auth/login';
      this.signUpUrl = environment.apiEndpoint + 'auth/usersignup';
    
   }

  saveToken(token: string):void{
    try{
      localStorage.setItem('token', token);
    }
    catch(err){
      console.error(err);
    }
  }
  getToken():string {
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

  sendLoginRequest(credentials:UserCredentials): Observable<any> {
    let body = {
      email: credentials.email,
      password: credentials.password,
    };
    return this.http.post<any>(this.loginUrl, body, this.getHttpOptions());
  }



}
