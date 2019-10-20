import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Track} from "../../model/track";
import {TrackService} from "../../service/track.service";

@Component({
  selector: 'app-track-list',
  templateUrl: './track-list.component.html',
  styleUrls: ['./track-list.component.css']
})
export class TrackListComponent implements OnInit {

  tracks: Observable<Track[]>;

  constructor(private service: TrackService) {
  }

  ngOnInit() {
    this.tracks = this.service.findAllTracks();
  }

  remove(track: Track) {
    this.service.removeTrack(track).subscribe(() => this.ngOnInit());
  }
}
