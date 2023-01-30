import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note } from 'src/app/models/note';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getUserNotes(id: number) {
    return this.http.get<Note[]>(
      environment.apiEndpoint + 'notes/getNotesByUserId/' + id,
      this.authService.getHttpOptionsWithToken()
    );
  }

  postNote(note: Note) {
    return this.http.post<any>(
      environment.apiEndpoint + 'notes/addNote',
      note,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
