import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../model/track";
import {Race} from "../model/race";
import {EmbeddedResource} from "../model/embedded-resource";

@Injectable({
  providedIn: 'root'
})
export class RaceService {

  constructor(private http: HttpClient) {
  }

  findAllRaces(): Observable<EmbeddedResource<Race[]>> {
    return this.http.get<EmbeddedResource<Race[]>>('api/races');
  }

  findRace(id: number): Observable<Race> {
    return this.http.get<Race>(`api/races/${id}`);
  }

  removeRace(race: Race): Observable<any> {
    return this.http.delete(`api/races/${race.id}`);
  }

  saveRace(race: Race): Observable<any> {
    if (race.id) {
      return this.http.put(`api/races/${race.id}`, race);
    } else {
      return this.http.post('api/races', race);
    }
  }

  findAllTracks(): Observable<EmbeddedResource<Track[]>> {
    return this.http.get<EmbeddedResource<Track[]>>('api/tracks');
  }
}
