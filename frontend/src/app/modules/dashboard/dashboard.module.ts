import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {DashboardRoutingModule} from "./dashboard-routing.module";
import {ToasterModule} from "angular2-toaster";
import {CategoryComponent} from './components/category/category.component';
import {ModuleComponent} from './components/module/module.component';
import {MatSortModule, MatTableModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    DashboardRoutingModule,
    ToasterModule.forRoot(),
    MatTableModule,
    MatSortModule
  ],
  providers: [],
  declarations: [DashboardComponent, CategoryComponent, ModuleComponent]
})
export class DashboardModule {
}
