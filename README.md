## 🎵 StreamingApp - Projeto de Streaming de Música
*AT da disciplina Design Patterns e Domain-Driven Design (DDD) com Java, da Gradução em Engenharia de Computação - Instituto INFNET*

### Visão Geral
O projeto StreamingApp é uma aplicação de streaming de música, semelhante ao Spotify, desenvolvida utilizando Java e Spring Boot. O sistema é projetado seguindo os princípios de Domain-Driven Design (DDD), S.O.L.I.D, e Clean Code para garantir uma arquitetura limpa, modular e extensível.

### Tecnologias Utilizadas
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Lombok

### Divisão em Subdomínios

O sistema é dividido em dois subdomínios principais:

1. **Subdomínio de Conteúdo**
   - Gerencia todo o conteúdo relacionado à música, como bandas, músicas e playlists.
   - Contém as entidades `Banda`, `Musica` e `Playlist`.

2. **Subdomínio de Usuários**
   - Gerencia tudo relacionado aos usuários, incluindo assinaturas, cartões de crédito e transações.
   - Contém as entidades `Usuario`, `Assinatura`, `Cartao`, `Transacao` e `Plano`.

### Bounded Contexts

Dentro de cada subdomínio, utilizou-se bounded contexts para delimitar claramente as responsabilidades e manter a coesão do sistema.

#### Subdomínio de Conteúdo

**Bounded Context de Conteúdo**
- **Entidades**:
  - `Banda`: Representa uma banda musical.
  - `Musica`: Representa uma música associada a uma banda.
  - `Playlist`: Representa uma playlist criada por um usuário, contendo várias músicas.
- **Serviços**:
  - `PlaylistService`: Gerencia a criação e manipulação de playlists.
  - `MusicaService`: Gerencia operações relacionadas às músicas.

**Controladores**:
- `PlaylistController`: Exposição dos endpoints para criação e manipulação de playlists.
- `MusicaController`: Exposição dos endpoints para operações relacionadas às músicas.

#### Subdomínio de Usuários

**Bounded Context de Usuários**
- **Entidades**:
  - `Usuario`: Representa um usuário do sistema.
  - `Assinatura`: Representa uma assinatura de plano de um usuário.
  - `Cartao`: Representa um cartão de crédito associado a um usuário.
  - `Transacao`: Representa uma transação realizada com um cartão de crédito.
  - `Plano`: Representa um plano de assinatura.
- **Value Objects**:
  - `CPF`: Representa um CPF validado de um usuário.
- **Serviços**:
  - `UsuarioService`: Gerencia operações relacionadas a usuários.
  - `AssinaturaService`: Gerencia operações relacionadas a assinaturas.
  - `TransacaoService`: Gerencia a autorização e criação de transações.
  - `AntifraudeService`: Implementa regras antifraude para validação de transações.

**Controladores**:
- `UsuarioController`: Exposição dos endpoints para criação de contas de usuário.
- `TransacaoController`: Exposição dos endpoints para autorização de transações.
- `AssinaturaController`: Exposição dos endpoints para criação de assinaturas.

### Benefícios da Estrutura de Subdomínios e Bounded Contexts

1. **Coesão e Separação de Responsabilidades**: Cada contexto delimitado possui uma responsabilidade clara e bem definida, evitando a mistura de lógica de diferentes domínios.
2. **Manutenibilidade**: Facilita a manutenção do código, permitindo que mudanças em um contexto não impactem outros contextos.
3. **Escalabilidade**: Permite escalar diferentes partes do sistema de forma independente, de acordo com as necessidades.
4. **Evolução Independente**: Cada bounded context pode evoluir de forma independente, adicionando novas funcionalidades sem afetar outras partes do sistema.

### Regras Técnicas

Vamos verificar cada uma das regras técnicas para garantir que o sistema está em conformidade com os requisitos.

#### 1. Utilizar pelo menos dois Design Patterns
- **Repository Pattern**: Está sendo utilizado nos repositórios `CartaoRepository`, `PlanoRepository`, `TransacaoRepository`, `UsuarioRepository` e `MusicaRepository`. Este padrão é usado para encapsular a lógica de acesso a dados e manipulação de banco de dados.
- **Service Layer Pattern**: Os serviços `PlaylistService`, `AntifraudeService`, `AssinaturaService`, `TransacaoService` e `UsuarioService` implementam a lógica de negócios e regras antifraude, separando-a da camada de apresentação e de persistência.

Portanto, o sistema está utilizando pelo menos dois Design Patterns.

#### 2. Aplicar Context Mapping utilizando Partnership, Customer-Supplier ou Anticorruption Layer
- **Context Mapping**: A comunicação entre os diferentes serviços (como `UsuarioService` e `PlaylistService`) indica uma abordagem de **Customer-Supplier**, onde `UsuarioService` depende de `PlaylistService` para criar a playlist padrão ao criar uma nova conta de usuário.

#### 3. Ter Aggregates, Aggregates Root e Domain Events
- **Aggregates e Aggregates Root**:
  - **Usuario**: O usuário é um aggregate root, agregando entidades como `Playlist`, `Cartao`, e `Assinatura`.
  - **Playlist**: A playlist é um aggregate root, agregando `Musica`.

- **Domain Events**:
  - **TransacaoCriadaEvent**: Evento de domínio publicado quando uma nova transação é autorizada no `TransacaoService`.

#### 4. Utilizar classes Entity e Value Objects
- **Entities**: As classes `Usuario`, `Assinatura`, `Cartao`, `Plano`, `Transacao`, `Playlist`, `Musica`, e `Banda` são entidades, pois possuem identificadores únicos e ciclo de vida próprio.
- **Value Objects**: A classe `CPF` é um exemplo de Value Object, pois é imutável e sua igualdade é baseada nos valores dos atributos, não em identidade.

### Princípios S.O.L.I.D e Clean Code
- **Single Responsibility Principle (SRP)**: Cada classe tem uma única responsabilidade, exemplificado pela separação entre serviços (`Service`), repositórios (`Repository`) e controladores (`Controller`).
- **Open/Closed Principle (OCP)**: O sistema é aberto para extensão, mas fechado para modificação. Novas regras de negócio podem ser adicionadas sem alterar o código existente.
- **Liskov Substitution Principle (LSP)**: As subclasses podem ser substituídas por suas superclasses sem quebrar a aplicação.
- **Interface Segregation Principle (ISP)**: Interfaces são específicas para cada funcionalidade, evitando métodos desnecessários.
- **Dependency Inversion Principle (DIP)**: A aplicação depende de abstrações (interfaces) e não de implementações concretas.

### Endpoints da API

#### PlaylistController
- `POST /api/v1/playlists`: Cria uma nova playlist.
- `POST /api/v1/playlists/{playlistId}/musicas/{musicaId}`: Adiciona uma música à playlist.
- `DELETE /api/v1/playlists/{playlistId}/musicas/{musicaId}`: Remove uma música da playlist.
- `POST /api/v1/playlists/favoritar`: Adiciona uma música à playlist de favoritas do usuário.

#### MusicaController
- `GET /api/v1/musicas/{musicaId}`: Retorna informações de uma música.

#### UsuarioController
- `POST /api/v1/usuarios/criarConta`: Cria uma nova conta de usuário.

#### TransacaoController
- `POST /api/v1/transacoes/autorizar`: Autoriza uma nova transação.

#### AssinaturaController
- `POST /api/v1/assinaturas/criar`: Cria uma nova assinatura para o usuário.

### Regras de Negócios Implementadas
#### **Regra 1**: O usuário pode ter somente um plano ativo.
  
**Classe:** `AssinaturaService`

**Método:** `criarAssinatura`

No método `criarAssinatura` da classe `AssinaturaService`, há uma verificação para garantir que o usuário não tenha mais de um plano ativo:

```java
public AssinaturaDTO criarAssinatura(AssinaturaDTO assinaturaDTO) throws Exception {
    Usuario usuario = usuarioRepository.findById(assinaturaDTO.getUsuarioId())
            .orElseThrow(() -> new Exception("Usuário não encontrado"));

    if (usuario.getAssinatura() != null && usuario.getAssinatura().isAtivo()) {
        throw new Exception("Usuário já possui uma assinatura ativa.");
    }

    Plano plano = planoRepository.findById(assinaturaDTO.getPlanoId())
            .orElseThrow(() -> new Exception("Plano não encontrado"));

    Assinatura assinatura = new Assinatura();
    assinatura.setPlano(plano);
    assinatura.setDtAssinatura(LocalDateTime.now());
    assinatura.setAtivo(true);
    usuario.setAssinatura(assinatura);
    usuarioRepository.save(usuario);

    return new AssinaturaDTO(assinatura);
}
```

Aqui, é verificado se o usuário já possui uma assinatura ativa (`usuario.getAssinatura().isAtivo()`), e uma exceção é lançada caso ele já tenha uma.
  
#### **Regra 2**: O usuário deve ter um cartão de crédito válido.
  
**Classe:** `Cartao`

**Método:** `validarCartao`

No método `validarCartao` da classe `Cartao`, a lógica de validação do cartão foi encapsulada no próprio modelo, garantindo que o cartão esteja ativo e válido:

```java
public void validarCartao() throws Exception {
    if (!this.ativo || this.validade.isBefore(LocalDate.now())) {
        throw new Exception("Cartão não é válido ou não está ativo.");
    }
}
```

**Classe:** `UsuarioService`

**Método:** `criarConta`

O método `criarConta` chama o método `validarCartao` do modelo `Cartao` para garantir que o cartão seja válido:

```java
public UsuarioDTO criarConta(UsuarioDTO usuarioDTO, UUID cartaoId, UUID planoId) throws Exception {
    CPF cpf = new CPF(usuarioDTO.getCpf());
    Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), passwordEncoder.encode(usuarioDTO.getSenha()), cpf);

    Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() -> new Exception("Cartão não encontrado"));
    Plano plano = planoRepository.findById(planoId).orElseThrow(() -> new Exception("Plano não encontrado"));

    // Validar o cartão usando o método do próprio modelo
    cartao.validarCartao();

    usuario.getCartoes().add(cartao);
    usuario.setAssinatura(new Assinatura(plano));

    usuarioRepository.save(usuario);

    playlistService.criarPlaylistDefault(usuario);

    return new UsuarioDTO(usuario);
}
```
  
#### **Regra 3**: Nenhuma transação deve ser aceita quando o cartão não está ativo.
  
**Classe:** `Cartao`

**Método:** `validarCartao`

Esta regra é coberta pela mesma lógica de validação no método `validarCartao` da classe `Cartao`:

```java
public void validarCartao() throws Exception {
    if (!this.ativo || this.validade.isBefore(LocalDate.now())) {
        throw new Exception("Cartão não é válido ou não está ativo.");
    }
}
```

**Classe:** `TransacaoService`

**Método:** `autorizarTransacao`

No método `autorizarTransacao`, o método `validarCartao` é chamado dentro do método `criarTransacao` para garantir que o cartão está ativo antes de processar a transação:

```java
public TransacaoDTO autorizarTransacao(TransacaoDTO transacaoDTO) throws Exception {
    Cartao cartao = cartaoRepository.findById(transacaoDTO.getCartaoId())
            .orElseThrow(() -> new Exception("Cartão não encontrado"));

    // Resto do código...

    cartao.criarTransacao(transacaoDTO.getMerchant(), transacaoDTO.getValor(), transacaoDTO.getDescricao());

    // Resto do código...
}
```
  
#### **Regra 4**: Não deve haver mais de 3 transações em um intervalo de 2 minutos.
  **Classe:** `Cartao`

**Método:** `validarTransacao`

No método `validarTransacao` da classe `Cartao`, há uma verificação para garantir que não haja mais de 3 transações em um intervalo de 2 minutos:

```java
private boolean validarTransacao(Transacao transacao) {
    List<Transacao> ultimasTransacoes = this.getTransacoes().stream()
            .filter((x) -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO)))
            .toList();

    if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA)
        return false;

    // Resto do código...
}
```

A lista `ultimasTransacoes` filtra transações ocorridas nos últimos 2 minutos, e o tamanho da lista é verificado para garantir que não exceda 3.
  
#### **Regra 5**: Não deve haver mais de 2 transações semelhantes (mesmo valor e comerciante) em um intervalo de 2 minutos.
  
**Classe:** `Cartao`

**Método:** `validarTransacao`

Ainda no método `validarTransacao` da classe `Cartao`, há uma verificação para garantir que não haja mais de 2 transações semelhantes em um intervalo de 2 minutos:

```java
private boolean validarTransacao(Transacao transacao) {
    List<Transacao> ultimasTransacoes = this.getTransacoes().stream()
            .filter((x) -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO)))
            .toList();

    if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA)
        return false;

    List<Transacao> transacoesMerchantRepetidas = ultimasTransacoes.stream()
            .filter((x) -> x.getComerciante().equals(transacao.getComerciante())
                    && x.getValor() == transacao.getValor())
            .toList();

    return transacoesMerchantRepetidas.size() < this.TRANSACAO_MERCHANT_DUPLICADAS;
}
```

A lista `transacoesMerchantRepetidas` filtra transações nos últimos 2 minutos que possuem o mesmo comerciante e valor, garantindo que o tamanho da lista não exceda 2.
  

### Testes
A aplicação possui testes foram escritos utilizando o JUnit e Mockito, cobrindo tanto as camadas de serviço quanto os controladores, para garantir a qualidade e confiabilidade do código.

#### Tipos de Testes Implementados
1. **Testes de Unidade**:
   - **Serviços**: Testes para `AntifraudeService`, `AssinaturaService`, `TransacaoService`, e `UsuarioService`, garantindo que a lógica de negócios está correta e as regras antifraude são aplicadas conforme esperado.
   - **Repositórios**: Testes para os repositórios `CartaoRepository`, `PlanoRepository`, `TransacaoRepository`, `UsuarioRepository`, e `MusicaRepository`, validando as operações de acesso a dados.

2. **Testes de Integração**:
   - **Controladores**: Testes para `PlaylistController`, `MusicaController`, `UsuarioController`, `TransacaoController`, e `AssinaturaController`, verificando se os endpoints estão funcionando corretamente e retornando as respostas esperadas.

#### Ferramentas Utilizadas
- **JUnit 5**: Framework de testes utilizado para escrever

 e executar testes de unidade e integração.
- **Mockito**: Utilizado para criação de mocks e simulação de comportamentos nos testes, permitindo testar componentes isolados.
- **Spring Boot Test**: Facilita a configuração do contexto de aplicação para testes de integração, fornecendo utilitários para testes de controladores e serviços.

#### Execução dos Testes
Para executar todos os testes, utilize o comando:
```sh
./mvnw test
```
Os resultados dos testes serão exibidos no console, indicando o sucesso ou falha de cada caso de teste.

### Como Executar
1. Clone o repositório.
2. Navegue até o diretório do projeto.
3. Execute o comando:
```sh
./mvnw spring-boot:run
```
5. Acesse a aplicação em `http://localhost:8080`.

### Conclusão
- **Operações**: ✔️
- **Regras de negócio**: ✔️
- **Dois Design Patterns**: ✔️
- **Context Mapping**: ✔️
- **Aggregates e Aggregates Root**: ✔️
- **Domain Events**: ✔️
- **Entity e Value Objects**: ✔️
