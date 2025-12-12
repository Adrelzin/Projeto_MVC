# Projeto_MVC
Projeto de uma loja usando SpringBoot

Usamos Java e Html para o funcionamento desse projeto, além da implementação de um banco de dados com MySql

O Projeto está dividido em:

# MODELS
Onde se encontram as entidades do sistema:
- **Cliente** - Cadastro de clientes com nome, email e telefone
- **Categoria** - Categorias para organização dos produtos
- **Produto** - Produtos da loja com preço, estoque e categoria
- **Pedido** - Pedidos realizados pelos clientes
- **ItemPedido** - Itens individuais de cada pedido
- **Pagamento** - Registro dos pagamentos dos pedidos

# CONTROLLERS
Divididos em dois tipos:

## REST Controllers (API)
Para acesso via requisições HTTP:
- `ClienteController`
- `CategoriaController`
- `ProdutoController`
- `PedidoController`
- `ItemPedidoController`
- `PagamentoController`

## View Controllers (Interface Web)
Para renderização das páginas HTML:
- `ClienteViewController`
- `CategoriaViewController`
- `ProdutoViewController`
- `PedidoViewController`
- `ItemPedidoViewController`
- `PagamentoViewController`

# REPOSITORIES
Interfaces JPA para acesso ao banco de dados:
- `ClienteRepository`
- `CategoriaRepository`
- `ProdutoRepository`
- `PedidoRepository`
- `ItemPedidoRepository`
- `PagamentoRepository`

# VIEWS (Templates HTML)
Páginas desenvolvidas com Thymeleaf e Bootstrap:

## Páginas de Listagem (com filtros de busca):
- `Clientes.html` - Lista clientes com busca por nome/email/telefone
- `Produtos.html` - Lista produtos com filtro por nome e categoria
- `Pedidos.html` - Lista pedidos com filtro por cliente e status
- `Categorias.html` - Lista todas as categorias
- `Pagamentos.html` - Lista todos os pagamentos
- `Itens-Pedido.html` - Visualização dos itens de cada pedido

## Páginas de Formulário:
- `ClienteForm.html` - Cadastro/edição de clientes
- `ProdutoForm.html` - Cadastro/edição de produtos
- `PedidoForm.html` - Cadastro/edição de pedidos (com seleção múltipla de produtos)
- `CategoriaForm.html` - Cadastro/edição de categorias
- `PagamentoForm.html` - Cadastro/edição de pagamentos
- `ItemPedidoForm.html` - Cadastro/edição de itens

## Página Principal:
index.html - Dashboard principal com acesso a todos os módulos

## Filtros de Busca
Clientes: Busca por nome, email ou telefone
Produtos: Busca por nome e filtro por categoria
Pedidos: Filtro por cliente e status do pedido


## Equipe
Adryell William Gomes Nascimento
Estéfanas Argentina Castanheira de Mesquita
Querén Hapuque do Céu Araújo de Morais Ferreira
