import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response: any) => {
        console.log('Successful login', response);
        // Store the token in localStorage
        if (response && response.token) {
          localStorage.setItem('authToken', response.token);
          // Navigates to the app
          this.router.navigate(['/app']);
        } else {
            console.error('Token not found');
            this.errorMessage = 'Ocorreu um erro inesperado. Tente novamente.';
        }
      },
      error: (error) => {
        console.error('Login error', error);
        this.errorMessage = 'Usuário ou senha inválidos. Tente novamente.';
      }
    });
  }
}
