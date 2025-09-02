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
      next: (response) => {
        // Lógica de sucesso - por exemplo, salvar o token e redirecionar
        console.log('Login bem-sucedido', response);
        // this.router.navigate(['/dashboard']); // Exemplo de redirecionamento
      },
      error: (error) => {
        console.error('Erro no login', error);
        this.errorMessage = 'Usuário ou senha inválidos. Tente novamente.';
      }
    });
  }
}
