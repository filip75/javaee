import {Component, OnInit} from '@angular/core';
import {Track} from "../../model/track";
import {ActivatedRoute, Router} from "@angular/router";
import {Race} from "../../model/race";
import {RaceService} from "../../service/race.service";

@Component({
  selector: 'app-race-edit',
  templateUrl: './race-edit.component.html',
  styleUrls: ['./race-edit.component.css']
})
export class RaceEditComponent implements OnInit {
  race: Race;
  availableTracks: Track[];

  constructor(private service: RaceService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id == null) {
      this.race = new Race();
    } else {
      this.service.findRace(Number(id)).subscribe(t => this.race = t);
    }
    this.service.findAllTracks().subscribe(tracks => this.availableTracks = tracks._embedded["tracks"])
  }

  save() {
    this.service.saveRace(this.race).subscribe(() => this.router.navigateByUrl('/races'));
  }

  compareTracks(t1: Track, t2: Track): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
}
