package pl.zzpj.solid.isp.contactbook.solution;

public class SegregationPrincipleGUD implements Dialler, Emailer {
    @Override
    public void makeCall(Contact dialable) {
        String telephone = dialable.getTelephone();

        // call using telephone
    }

    @Override
    public void sendMessage(Contact emailable, String subject, String body) {
        String emailAddress = emailable.getEmailAddress();

        sendEmail(emailAddress, subject, body);
    }

    @Override
    public void sendEmail(String emailAddress, String subject, String body) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        SegregationPrincipleGUD interfaceSegregationPrinciple = new SegregationPrincipleGUD();
        interfaceSegregationPrinciple.contactPeople();
    }

    public void contactPeople() {
        Contact contact = new Contact("Jan Kowalski", "Kielce", "jan.kowalski@gmail.com", "83744-23434");
        this.sendMessage(contact, "promocja", "tanio dzisiaj!");
        this.makeCall(contact);
    }
}
