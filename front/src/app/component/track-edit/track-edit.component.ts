import {Component, OnInit} from '@angular/core';
import {TrackService} from "../../service/track.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Track} from "../../model/track";

@Component({
  selector: 'app-track-edit',
  templateUrl: './track-edit.component.html',
  styleUrls: ['./track-edit.component.css']
})
export class TrackEditComponent implements OnInit {
  track: Track;

  constructor(private service: TrackService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.track = new Track();
    } else {
      this.service.findTrack(Number(id)).subscribe(t => this.track = t);
    }
  }

  save(){
    this.service.saveTrack(this.track).subscribe(()=>this.router.navigateByUrl('/tracks'));
  }
}
