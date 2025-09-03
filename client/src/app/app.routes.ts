import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { MainLayout } from './layouts/main-layout/main-layout';
import { authGuard } from './auth/auth-guard';
import { MyReviews } from './pages/my-reviews/my-reviews';
import { WineList } from './pages/wine-list/wine-list';
import { Profile } from './pages/profile/profile';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'app',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      { path : '', redirectTo: 'my-reviews', pathMatch: 'full' },
      { path: 'my-reviews', component: MyReviews },
      { path: 'wine-list', component: WineList },
      { path: 'profile', component: Profile }
    ]
  },

  { path: '**', redirectTo: '/login'}
];
