import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Race} from "../../model/race";
import {RaceService} from "../../service/race.service";

@Component({
  selector: 'app-race-list',
  templateUrl: './race-list.component.html',
  styleUrls: ['./race-list.component.css']
})
export class RaceListComponent implements OnInit {

  races: Observable<Race[]>;

  constructor(private service: RaceService) {
  }

  ngOnInit() {
    this.races = this.service.findAllRaces();
  }

  remove(race: Race) {
    this.service.removeRace(race).subscribe(() => this.ngOnInit());
  }
}
