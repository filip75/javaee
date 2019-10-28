import {Component, OnInit} from '@angular/core';
import {Track} from "../../model/track";
import {TrackService} from "../../service/track.service";
import {Link} from "../../model/link";
import {Router} from "@angular/router";

@Component({
  selector: 'app-track-list',
  templateUrl: './track-list.component.html',
  styleUrls: ['./track-list.component.css']
})
export class TrackListComponent implements OnInit {

  tracks: Track[];
  links: Map<String, Link>;

  constructor(private service: TrackService, private router: Router) {
  }

  ngOnInit() {
    this.service.findAllTracks().subscribe(v => {
      this.tracks = v._embedded["tracks"];
      this.links = v._links;
    });
  }

  remove(track: Track) {
    this.service.removeTrack(track).subscribe(() => this.ngOnInit());
  }

  first() {
    console.log(this.links["first"]);
    this.router.navigateByUrl(this.links["first"]);
  }
}
