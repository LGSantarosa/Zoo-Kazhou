document.addEventListener('DOMContentLoaded', () => {
    const app = document.getElementById('app');
    const nav = document.getElementById('nav-principal');
    
    const API_URL = 'http://localhost:8080/api';

    const state = {
        currentUser: sessionStorage.getItem('user'),
        currentPage: 'login', // 'login', 'register', 'animais', 'tarefas'
    };

    // --- API HELPER ---
    async function apiRequest(method, path, body = null) {
        const options = {
            method,
            headers: { 'Content-Type': 'application/json' },
        };
        if (body) {
            options.body = JSON.stringify(body);
        }
        
        try {
            const response = await fetch(API_URL + path, options);
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({ erro: 'Erro desconhecido' }));
                throw new Error(errorData.erro || `HTTP error! status: ${response.status}`);
            }
            if (response.status === 204) { // No Content
                return null;
            }
            return response.json();
        } catch (error) {
            console.error('API Request Error:', error);
            throw error;
        }
    }

    // --- TEMPLATES / RENDER FUNCTIONS ---
    function render() {
        updateNav();
        app.innerHTML = ''; // Limpa o conteúdo
        switch (state.currentPage) {
            case 'login':
                app.innerHTML = templates.login();
                break;
            case 'register':
                app.innerHTML = templates.register();
                break;
            case 'animais':
                renderAnimais();
                break;
            case 'tarefas':
                renderTarefas();
                break;
            default:
                app.innerHTML = templates.login();
        }
    }
    
    function updateNav() {
        if (state.currentUser) {
            nav.innerHTML = `
                <a href="#" data-page="animais">Animais</a>
                <a href="#" data-page="tarefas">Tarefas</a>
                <a href="#" id="logout-btn">Logout</a>
            `;
        } else {
            nav.innerHTML = `
                <a href="#" data-page="login">Login</a>
                <a href="#" data-page="register">Registrar</a>
            `;
        }
    }
    
    const templates = {
        login: () => `
            <div class="form-container">
                <h2>Login</h2>
                <div class="error-message"></div>
                <form id="login-form">
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" id="login" name="login" required>
                    </div>
                    <div class="form-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" required>
                    </div>
                    <button type="submit" class="btn">Entrar</button>
                </form>
            </div>
        `,
        register: () => `
            <div class="form-container">
                <h2>Registrar Novo Funcionário</h2>
                 <div class="error-message"></div>
                <form id="register-form">
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input type="text" id="login" name="login" required>
                    </div>
                    <div class="form-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" required>
                    </div>
                    <button type="submit" class="btn">Registrar</button>
                </form>
            </div>
        `,
        animais: (animais) => `
            <section class="content-section">
                <div class="content-header">
                    <h2>Gerenciamento de Animais</h2>
                    <button class="btn" id="add-animal-btn">Adicionar Animal</button>
                </div>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Espécie</th>
                            <th>Idade</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${animais.map(animal => `
                            <tr>
                                <td>${animal.id}</td>
                                <td>${animal.nome}</td>
                                <td>${animal.especie}</td>
                                <td>${animal.idade}</td>
                                <td>
                                    <button class="btn btn-secondary" data-animal-id="${animal.id}">Ocorrência</button>
                                    <button class="btn btn-danger" data-animal-id="${animal.id}">Remover</button>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            </section>
        `,
        tarefas: (tarefas) => `
            <section class="content-section">
                <div class="content-header">
                    <h2>Lista de Tarefas</h2>
                     <button class="btn" id="add-tarefa-btn">Adicionar Tarefa</button>
                </div>
                <table class="data-table">
                     <thead>
                        <tr>
                            <th>ID</th>
                            <th>Descrição</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tarefas.map(tarefa => `
                            <tr class="${tarefa.concluida ? 'concluida' : ''}">
                                <td>${tarefa.id}</td>
                                <td>${tarefa.descricao}</td>
                                <td>${tarefa.concluida ? 'Concluída' : 'Pendente'}</td>
                                <td>
                                    ${!tarefa.concluida ? `<button class="btn" data-tarefa-id="${tarefa.id}">Concluir</button>` : ''}
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            </section>
        `,
        addAnimalForm: () => `
            <div class="modal-overlay">
                <div class="form-container">
                    <span class="close-modal">&times;</span>
                    <h2>Adicionar Novo Animal</h2>
                    <form id="add-animal-form">
                        <div class="form-group">
                            <label for="nome">Nome</label>
                            <input type="text" id="nome" name="nome" required>
                        </div>
                        <div class="form-group">
                            <label for="especie">Espécie</label>
                            <input type="text" id="especie" name="especie" required>
                        </div>
                         <div class="form-group">
                            <label for="idade">Idade</label>
                            <input type="number" id="idade" name="idade" required>
                        </div>
                        <button type="submit" class="btn">Salvar</button>
                    </form>
                </div>
            </div>
        `,
        addTarefaForm: () => `
            <div class="modal-overlay">
                <div class="form-container">
                    <span class="close-modal">&times;</span>
                    <h2>Adicionar Nova Tarefa</h2>
                    <form id="add-tarefa-form">
                        <div class="form-group">
                            <label for="descricao">Descrição</label>
                            <input type="text" id="descricao" name="descricao" required>
                        </div>
                        <button type="submit" class="btn">Salvar</button>
                    </form>
                </div>
            </div>
        `,
        addOcorrenciaForm: (animalId) => `
            <div class="modal-overlay">
                <div class="form-container">
                    <span class="close-modal">&times;</span>
                    <h2>Registrar Ocorrência</h2>
                    <form id="add-ocorrencia-form" data-animal-id="${animalId}">
                        <div class="form-group">
                            <label for="descricao">Descrição da Ocorrência</label>
                            <input type="text" id="descricao" name="descricao" required>
                        </div>
                        <button type="submit" class="btn">Registrar</button>
                    </form>
                </div>
            </div>
        `,
    };

    async function renderAnimais() {
        try {
            const animais = await apiRequest('GET', '/animais');
            app.innerHTML = templates.animais(animais);
        } catch (error) {
            app.innerHTML = `<p class="error-message">Não foi possível carregar os animais.</p>`;
        }
    }

    async function renderTarefas() {
        try {
            const tarefas = await apiRequest('GET', '/tarefas');
            app.innerHTML = templates.tarefas(tarefas);
        } catch (error) {
            app.innerHTML = `<p class="error-message">Não foi possível carregar as tarefas.</p>`;
        }
    }
    
    // --- EVENT HANDLERS ---
    
    // delegação de eventos de navegação
    nav.addEventListener('click', (e) => {
        e.preventDefault();
        if (e.target.matches('a[data-page]')) {
            state.currentPage = e.target.dataset.page;
            render();
        }
        if (e.target.matches('#logout-btn')) {
            sessionStorage.removeItem('user');
            state.currentUser = null;
            state.currentPage = 'login';
            render();
        }
    });
    
    // delegação de eventos de formulário e botões
     app.addEventListener('click', async (e) => {
        // Remover animal
        if (e.target.matches('.btn-danger[data-animal-id]')) {
            const animalId = e.target.dataset.animalId;
            if (confirm(`Tem certeza que deseja remover o animal ${animalId}?`)) {
                try {
                    await apiRequest('DELETE', `/animais/${animalId}`);
                    renderAnimais(); // Re-renderiza a lista
                } catch (error) {
                    alert(`Erro ao remover animal: ${error.message}`);
                }
            }
        }

        // Concluir tarefa
        if (e.target.matches('.btn[data-tarefa-id]')) {
            const tarefaId = e.target.dataset.tarefaId;
            try {
                await apiRequest('PUT', `/tarefas/${tarefaId}/concluir`);
                renderTarefas(); // Re-renderiza a lista
            } catch (error) {
                alert(`Erro ao concluir tarefa: ${error.message}`);
            }
        }

        // Adicionar Animal (abre formulário)
        if (e.target.matches('#add-animal-btn')) {
            app.insertAdjacentHTML('beforeend', templates.addAnimalForm());
        }

        // Adicionar Tarefa (abre formulário)
        if (e.target.matches('#add-tarefa-btn')) {
            app.insertAdjacentHTML('beforeend', templates.addTarefaForm());
        }
        
        // Adicionar Ocorrência (abre formulário)
        if (e.target.matches('.btn-secondary[data-animal-id]')) {
            const animalId = e.target.dataset.animalId;
            app.insertAdjacentHTML('beforeend', templates.addOcorrenciaForm(animalId));
        }

        // Fechar Modal/Formulário
        if (e.target.matches('.close-modal')) {
            e.target.closest('.modal-overlay').remove();
        }
     });

     app.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Login
        if (e.target.matches('#login-form')) {
            const form = e.target;
            const errorMessageDiv = form.parentElement.querySelector('.error-message');
            errorMessageDiv.textContent = ''; // Limpa erros antigos

            const data = Object.fromEntries(new FormData(form).entries());
            try {
                const result = await apiRequest('POST', '/funcionarios/login', data);
                state.currentUser = result.funcionarioId;
                sessionStorage.setItem('user', result.funcionarioId);
                state.currentPage = 'animais';
                render();
            } catch (error) {
                errorMessageDiv.textContent = error.message;
            }
        }

        // Registro
        if (e.target.matches('#register-form')) {
            const form = e.target;
            const errorMessageDiv = form.parentElement.querySelector('.error-message');
            errorMessageDiv.textContent = ''; // Limpa erros antigos

            const data = Object.fromEntries(new FormData(form).entries());
            try {
                await apiRequest('POST', '/funcionarios/registrar', data);
                alert('Registro bem-sucedido! Faça o login.');
                state.currentPage = 'login';
                render();
            } catch (error) {
                errorMessageDiv.textContent = error.message;
            }
        }
        
        // --- Forms dinâmicos ---

        // Adicionar Animal
        if (e.target.matches('#add-animal-form')) {
             const form = e.target;
             const data = Object.fromEntries(new FormData(form).entries());
             data.idade = parseInt(data.idade, 10); // Converte idade para número
             try {
                 await apiRequest('POST', '/animais', data);
                 form.closest('.modal-overlay').remove();
                 renderAnimais();
             } catch (error) {
                 alert(`Erro: ${error.message}`);
             }
        }

        // Adicionar Tarefa
        if (e.target.matches('#add-tarefa-form')) {
             const form = e.target;
             const data = Object.fromEntries(new FormData(form).entries());
             try {
                 await apiRequest('POST', '/tarefas', data);
                 form.closest('.modal-overlay').remove();
                 renderTarefas();
             } catch (error) {
                 alert(`Erro: ${error.message}`);
             }
        }
        
        // Adicionar Ocorrência
        if (e.target.matches('#add-ocorrencia-form')) {
             const form = e.target;
             const data = Object.fromEntries(new FormData(form).entries());
             const animalId = form.dataset.animalId;
             try {
                 await apiRequest('POST', `/animais/${animalId}/ocorrencias`, { descricao: data.descricao });
                 form.closest('.modal-overlay').remove();
                 alert('Ocorrência registrada com sucesso!');
             } catch (error) {
                 alert(`Erro: ${error.message}`);
             }
        }
    });

    // --- INIT ---
    function init() {
        if (state.currentUser) {
            state.currentPage = 'animais';
        }
        render();
    }

    init();
}); 