import {Component, OnInit} from '@angular/core';
import {Race} from "../../model/race";
import {RaceService} from "../../service/race.service";

@Component({
  selector: 'app-race-list',
  templateUrl: './race-list.component.html',
  styleUrls: ['./race-list.component.css']
})
export class RaceListComponent implements OnInit {

  races: Race[];

  constructor(private service: RaceService) {
  }

  ngOnInit() {
    this.service.findAllRaces().subscribe(value => this.races = value._embedded["races"]);
  }

  remove(race: Race) {
    this.service.removeRace(race).subscribe(() => this.ngOnInit());
  }
}
