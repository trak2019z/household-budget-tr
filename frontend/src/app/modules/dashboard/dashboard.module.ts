import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {DashboardRoutingModule} from "./dashboard-routing.module";
import {ToasterModule} from "angular2-toaster";
import {CategoryComponent} from './components/category/category.component';
import {ModuleComponent} from './components/module/module.component';
import {
  MatCardModule,
  MatDialogModule,
  MatFormFieldModule, MatIconModule,
  MatInputModule, MatPaginatorModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule, MatTooltipModule
} from "@angular/material";
import {CategoryService} from "./services/category.service";
import {ModuleService} from "./services/module.service";
import { DialogConfirmDeleteComponent } from './components/dialog-confirm-delete/dialog-confirm-delete.component';

@NgModule({
  imports: [
    CommonModule,
    DashboardRoutingModule,
    ToasterModule.forRoot(),
    MatTableModule,
    MatSortModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDialogModule,
    MatPaginatorModule,
    MatIconModule,
    MatTooltipModule
  ],
  entryComponents: [DialogConfirmDeleteComponent],
  providers: [CategoryService, ModuleService],
  declarations: [DashboardComponent, CategoryComponent, ModuleComponent, DialogConfirmDeleteComponent]
})
export class DashboardModule {
}
