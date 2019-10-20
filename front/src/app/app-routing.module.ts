import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackListComponent} from "./component/track-list/track-list.component";
import {TrackEditComponent} from "./component/track-edit/track-edit.component";


const routes: Routes = [
  {path: 'tracks', component: TrackListComponent},
  {path: 'tracks/:id/edit', component: TrackEditComponent},
  {path: 'tracks/create', component: TrackEditComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
