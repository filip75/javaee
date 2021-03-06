import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './component/app/app.component';
import {TrackListComponent} from './component/track-list/track-list.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {TrackService} from "./service/track.service";
import {TrackEditComponent} from './component/track-edit/track-edit.component';
import {RaceListComponent} from './component/race-list/race-list.component';
import {RaceEditComponent} from './component/race-edit/race-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    TrackListComponent,
    TrackEditComponent,
    RaceListComponent,
    RaceEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [TrackService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
