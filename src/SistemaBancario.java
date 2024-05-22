import java.util.HashMap;
import java.util.Scanner;

public class SistemaBancario {

    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scanner);
                    break;
                case 2:
                    fazerLogin(scanner);
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }

         */
        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) {
                cadastrarUsuario(scanner);
            } else if (opcao.equals("2")) {
                fazerLogin(scanner);
            } else if (opcao.equals("3")) {
                System.out.println("Saindo...");
                return;
            } else {
                System.out.println("Comando inválido!");
            }
        }
        //====================================================
    }

    private static void exibirMenu() {
        System.out.println("Bem-vindo ao Sistema Bancário");
        System.out.println("1. Cadastro");
        System.out.println("2. Login");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarUsuario(Scanner scanner) {
        System.out.println("Cadastro de Usuário");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Tipo de conta: ");
        String tipoConta = scanner.nextLine();
        System.out.print("Saldo inicial: ");
        double saldoInicial = scanner.nextDouble();

        Usuario novoUsuario = new Usuario(nome, senha, telefone, tipoConta, saldoInicial);
        usuarios.put(nome, novoUsuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void fazerLogin(Scanner scanner) {
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        if (usuarios.containsKey(nomeUsuario) && usuarios.get(nomeUsuario).getSenha().equals(senha)) {
            usuarioLogado = usuarios.get(nomeUsuario);
            System.out.println("Login bem-sucedido!");

            while (true) {
                exibirMenuLogado();
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do scanner

                switch (opcao) {
                    case 1:
                        //exibirInformacoesUsuario();
                        System.out.print("Digite o valor a ser depositado: ");
                        double valorDeposito = scanner.nextDouble();
                        usuarioLogado.depositar(valorDeposito);
                        break;
                    case 2:
                        //System.out.println("Saldo: " + usuarioLogado.getSaldo());
                        System.out.print("Digite o valor a ser sacado: ");
                        double valorSaque = scanner.nextDouble();
                        usuarioLogado.sacar(valorSaque);
                        break;
                    case 3:
                        System.out.print("Digite o nome do destinatário: ");
                        String destinatario = scanner.nextLine();
                        System.out.print("Digite o valor a ser transferido: ");
                        double valorTransferencia = scanner.nextDouble();
                        Usuario usuarioDestinatario = usuarios.get(destinatario);
                        if (usuarioDestinatario != null) {
                            usuarioLogado.transferir(usuarioDestinatario, valorTransferencia);
                        } else {
                            System.out.println("Usuário destinatário não encontrado!");
                        }
                        break;
                    case 4:
                        usuarioLogado = null; // Fazer logout
                        System.out.println("Logout bem-sucedido!");
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } else {
            System.out.println("Usuário ou senha incorretos!");
        }
    }

    private static void exibirMenuLogado() {
        System.out.println("Bem-vindo, " + usuarioLogado.getNome() + "!");
        System.out.println(" Saldo - R$ " + usuarioLogado.getSaldo());
        System.out.println("1. Depósito");
        System.out.println("2. Saque");
        System.out.println("3. Transferência");
        System.out.println("4. Logout");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirInformacoesUsuario() {
        System.out.println("Nome: " + usuarioLogado.getNome());
        System.out.println("Saldo: " + usuarioLogado.getSaldo());
    }

    private static class Usuario {
        private String nome;
        private String senha;
        private String telefone;
        private String tipoConta;
        private double saldo;

        public Usuario(String nome, String senha, String telefone, String tipoConta, double saldo) {
            this.nome = nome;
            this.senha = senha;
            this.telefone = telefone;
            this.tipoConta = tipoConta;
            this.saldo = saldo;
        }

        public String getNome() {
            return nome;
        }

        public String getSenha() {
            return senha;
        }

        public double getSaldo() {
            return saldo;
        }

        public void depositar(double valor) {
            saldo += valor;
            System.out.println("Depósito de " + valor + " realizado com sucesso. Novo saldo: " + saldo);
        }

        public void sacar(double valor) {
            if (saldo >= valor) {
                saldo -= valor;
                System.out.println("Saque de " + valor + " realizado com sucesso. Novo saldo: " + saldo);
            } else {
                System.out.println("Saldo insuficiente!");
            }
        }

        public void transferir(Usuario destinatario, double valor) {
            if (saldo >= valor) {
                saldo -= valor;
                destinatario.depositar(valor);
                System.out.println("Transferência de " + valor + " para " + destinatario.nome + " realizada com sucesso.");
            } else {
                System.out.println("Saldo insuficiente!");
            }
        }
    }
}
