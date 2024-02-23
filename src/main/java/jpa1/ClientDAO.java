package jpa1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClientDAO {
    private final EntityManagerFactory factory;

    public ClientDAO() {
        this.factory = Persistence.createEntityManagerFactory("homework7");

    }

    public void saveClientArray(Client[] clients) {
        for (Client client : clients) {
            saveClient(client);
        }
    }

    public void saveClient(Client client) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(client);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static Client createClient(String name, int age) {

        Client client = new Client(name, age);

        client.addAccount(new Account(TYPE.EUR, client));
        client.addAccount(new Account(TYPE.USD, client));
        client.addAccount(new Account(TYPE.UAH, client));

        return client;
    }

    public Client createAndSaveClient(String name, int age) {
        Client client = createClient(name, age);
        saveClient(client);

        return client;
    }

    public Client getClientById(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            Client result = em.find(Client.class, id);
            return result;
        } finally {
            em.close();
        }
    }

    public void topUpAccount(double amount, TYPE type, Client client) {
        EntityManager em = factory.createEntityManager();
        try {
            if (getClientById(client.getId()) == null) {
                saveClient(client);
                em.getTransaction().begin();
                client.getAccountByType(type).addBalance(amount);
                client.addIncomeTransaction(new Transaction(amount, type, null, client));
                em.getTransaction().commit();
            } else {
                em.getTransaction().begin();
                client.getAccountByType(type).addBalance(amount);
                client.addIncomeTransaction(new Transaction(amount, type, null, client));
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    public List<Client> getAllClients() {

        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Client> resultQuery = em.createQuery("SELECT x FROM Client x", Client.class);

            return resultQuery.getResultList();
        } finally {
            em.close();
        }
    }

}
