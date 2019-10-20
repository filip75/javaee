import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../model/track";

@Injectable({
  providedIn: 'root'
})
export class TrackService {

  constructor(private http: HttpClient) {
  }

  findAllTracks(): Observable<Track[]> {
    return this.http.get<Track[]>('api/tracks');
  }

  findTrack(id: number): Observable<Track> {
    return this.http.get<Track>(`api/tracks/${id}`);
  }

  removeTrack(track: Track): Observable<any> {
    return this.http.delete(`api/tracks/${track.id}`);
  }

  saveTrack(track: Track): Observable<any> {
    if (track.id) {
      return this.http.put(`api/tracks/${track.id}`, track);
    } else {
      return this.http.post('api/tracks', track);
    }
  }
}
