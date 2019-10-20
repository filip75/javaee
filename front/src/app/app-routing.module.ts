import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackListComponent} from "./component/track-list/track-list.component";
import {TrackEditComponent} from "./component/track-edit/track-edit.component";
import {RaceListComponent} from "./component/race-list/race-list.component";
import {RaceEditComponent} from "./component/race-edit/race-edit.component";


const routes: Routes = [
  {path: 'tracks', component: TrackListComponent},
  {path: 'tracks/:id/edit', component: TrackEditComponent},
  {path: 'tracks/create', component: TrackEditComponent},
  {path: 'races', component: RaceListComponent},
  {path: 'races/:id/edit', component: RaceEditComponent},
  {path: 'races/create', component: RaceEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
