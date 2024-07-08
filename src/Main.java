import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// Criando o cliente Carlos Dias
		Cliente carlos = new Cliente();
		carlos.setNome("Carlos Dias");
		
		// Criando contas para Carlos Dias
		Conta ccCarlos = new ContaCorrente(carlos, "120304");
		ccCarlos.setAgencia(566);
		ccCarlos.setNumero(12045);
		
		Conta poupancaCarlos = new ContaPoupanca(carlos, "120304");
		poupancaCarlos.setAgencia(566);
		poupancaCarlos.setNumero(12046);
		
		System.out.println("Bem-vindo ao Banco!");
		System.out.print("Digite a agência: ");
		int agencia = scanner.nextInt();
		System.out.print("Digite o número da conta: ");
		int numero = scanner.nextInt();
		scanner.nextLine();  // Consumir a nova linha
		System.out.print("Digite a senha: ");
		String senha = scanner.nextLine();

		Conta conta = autenticar(ccCarlos, agencia, numero, senha) ? ccCarlos : 
                      autenticar(poupancaCarlos, agencia, numero, senha) ? poupancaCarlos : null;

		if (conta != null) {
			int tentativas = 0;
			while (tentativas < 2 && !autenticar(conta, agencia, numero, senha)) {
				System.out.println("Senha incorreta. Tente novamente.");
				System.out.print("Digite a senha: ");
				senha = scanner.nextLine();
				tentativas++;
			}

			if (!autenticar(conta, agencia, numero, senha)) {
				System.out.println("Senha incorreta. Acesso negado.");
				return;
			}
			
			int opcao;
			do {
				System.out.println("Menu:");
				System.out.println("1. Depósito");
				System.out.println("2. Saque");
				System.out.println("3. Extrato");
				System.out.println("4. Sair");
				System.out.print("Escolha uma opção: ");
				opcao = scanner.nextInt();

				switch (opcao) {
					case 1:
						System.out.print("Digite o valor para depósito: ");
						double valorDeposito = scanner.nextDouble();
						conta.depositar(valorDeposito);
						System.out.println("Depósito realizado com sucesso.");
						break;
					case 2:
						System.out.print("Digite o valor para saque: ");
						double valorSaque = scanner.nextDouble();
						if (conta.getSaldo() >= valorSaque) {
							conta.sacar(valorSaque);
							System.out.println("Saque realizado com sucesso.");
						} else {
							System.out.println("Saldo insuficiente.");
						}
						break;
					case 3:
						System.out.println("Escolha o período do extrato:");
						System.out.println("1. Últimos 10 dias");
						System.out.println("2. Último mês");
						int opcaoExtrato = scanner.nextInt();
						conta.imprimirExtrato();
						// Aqui, você pode adicionar a lógica para filtrar as transações conforme o período
						break;
					case 4:
						System.out.println("Saindo...");
						break;
					default:
						System.out.println("Opção inválida.");
				}
			} while (opcao != 4);

		} else {
			System.out.println("Dados incorretos. Acesso negado.");
		}

		scanner.close();
	}

	private static boolean autenticar(Conta conta, int agencia, int numero, String senha) {
		return conta.getAgencia() == agencia && conta.getNumero() == numero && conta.getSenha().equals(senha);
	}
}