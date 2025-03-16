# DSCommerce

## 1. Visão Geral do Sistema

O sistema permite o gerenciamento de usuários, produtos e categorias. As principais funcionalidades incluem:

- **Cadastro de usuários**, contendo nome, e-mail, telefone, data de nascimento e senha de acesso.
- **Gerenciamento de produtos** com nome, descrição, preço e imagem.
- **Catálogo de produtos** com opção de filtro por nome.
- **Carrinho de compras**, permitindo adicionar, remover e alterar quantidades de produtos.
- **Processo de pedido**, armazenando dados como instante da compra, status e lista de itens adquiridos.
- **Diferentes perfis de usuários**: Cliente e Administrador.
- **Administração do sistema**, permitindo que usuários com permissão gerenciem cadastros de usuários, produtos e categorias.

## 2. Modelo Conceitual

- Cada item de pedido (`OrderItem`) corresponde a um produto, registrando quantidade e preço.
- Usuários podem ter um ou mais *roles* de acesso (`Client`, `Admin`).

## 3. Endpoints da API

| Método  | Endpoint                | Descrição                                      | Acesso       |
|---------|-------------------------|----------------------------------------------|--------------|
| `GET`   | `/orders/{id}`          | Obter pedido por ID                         | Usuário logado |
| `GET`   | `/products/{id}`        | Obter produto por ID                        | Público       |
| `GET`   | `/products`             | Listar todos os produtos                    | Público       |
| `GET`   | `/user/me`              | Obter informações do usuário logado         | Usuário logado |
| `GET`   | `/categories`           | Listar categorias                           | Público       |
| `POST`  | `/oauth2/token`                | Autenticar usuário                          | Público       |
| `POST`  | `/orders`               | Criar um novo pedido                        | Usuário logado |
| `POST`  | `/products`             | Criar um novo produto                       | Somente Admin |
| `PUT`   | `/products/{id}`        | Atualizar um produto                        | Somente Admin |
| `DELETE`| `/products/{id}`        | Remover um produto                          | Somente Admin |

## 4. Atores do Sistema

| Ator              | Responsabilidades |
|------------------|------------------|
| **Usuário anônimo** | Consultar catálogo, gerenciar carrinho e login. |
| **Cliente**       | Gerenciar seu perfil e visualizar histórico de pedidos. |
| **Admin**         | Acesso total ao sistema, incluindo cadastros e relatórios. |

## 5. Casos de Uso (Detalhamento)

### **Consultar Catálogo**
- **Atores:** Usuário anônimo, Cliente, Admin  
- **Pré-condições:** Nenhuma  
- **Pós-condições:** Nenhuma  
- **Descrição:** Exibir produtos disponíveis com opção de filtro por nome.  

#### **Fluxo principal:**
1. O sistema exibe uma lista paginada de produtos.
2. O usuário pode buscar por nome.
3. O sistema atualiza a listagem conforme a pesquisa.

#### **Regras de negócio:**
- A listagem é paginada (12 produtos por página).

---

### **Manter Produtos**
- **Atores:** Admin  
- **Pré-condições:** Usuário logado  
- **Pós-condições:** Nenhuma  
- **Descrição:** CRUD de produtos, com filtragem por nome.  

#### **Fluxo principal:**
1. O Admin acessa o gerenciamento de produtos.
2. Escolhe uma das opções:
   - **Inserir**: Informa nome, preço, descrição, imagem e categorias.
   - **Atualizar**: Seleciona um produto, edita os dados e salva.
   - **Deletar**: Seleciona um produto para remoção.

#### **Regras de validação:**
- **Nome**: 3 a 80 caracteres.
- **Preço**: Valor positivo.
- **Descrição**: Mínimo 10 caracteres.
- Pelo menos uma categoria deve ser selecionada.

---

### **Login**
- **Atores:** Usuário anônimo  
- **Pré-condições:** Nenhuma  
- **Pós-condições:** Usuário logado  
- **Descrição:** Autenticação no sistema.  

#### **Fluxo principal:**
1. Usuário insere e-mail e senha.
2. O sistema valida as credenciais e retorna um token de autenticação.

#### **Fluxo alternativo:**
- **Credenciais inválidas**: O sistema exibe uma mensagem de erro.

---

### **Gerenciar Carrinho**
- **Atores:** Usuário anônimo  
- **Pré-condições:** Nenhuma  
- **Pós-condições:** Nenhuma  
- **Descrição:** Adicionar, remover e atualizar quantidades de produtos no carrinho.  

#### **Fluxo principal:**
1. O usuário seleciona um produto no catálogo.
2. O sistema exibe os detalhes do produto.
3. O usuário adiciona o produto ao carrinho.
4. O sistema exibe o carrinho atualizado.
5. O usuário pode alterar quantidades ou remover itens.

#### **Estrutura do carrinho:**
- Lista de itens com **id, nome, preço, quantidade e subtotal**.
- Valor total do carrinho.

---

### **Registrar Pedido**
- **Atores:** Cliente  
- **Pré-condições:** Usuário logado, carrinho não vazio  
- **Pós-condições:** Carrinho esvaziado, pedido registrado  
- **Descrição:** Criar um pedido com os itens do carrinho.  

#### **Fluxo principal:**
1. O cliente confirma os itens do carrinho.
2. O sistema registra o pedido e gera um número de identificação.
