package Client_pack;
import Graphic.AgentGui;
import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentDemandeur extends Agent{
	private AgentGui Gui;
	private String mot;
	private String rep=null;
	private DFAgentDescription[] agentsTraducteurs;
	private MessageTemplate mt;
	
	protected void  setup() {
		System.out.println("le Demandeur "+getAID().getName()+" est pret");
		Gui=new AgentGui(this);
		Gui.affiche();
		
	}
	
	public void updatemot(String mott){
		mot=mott;
		addBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				DFAgentDescription modele=new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription( ) ;
				sd.setType( "Traducteur" ) ;
				modele.addServices( sd ) ;
				try {
					agentsTraducteurs = DFService.search( myAgent ,modele);}
					catch ( FIPAException fe ) { fe.printStackTrace () ; }
				// envoyer le cfp a tous les traducteur
					if(mot!=null){
						ACLMessage cfp = new ACLMessage ( ACLMessage.CFP) ;
						for( int i = 0 ; i < agentsTraducteurs . length ; ++i ){
							cfp.addReceiver(agentsTraducteurs[ i ].getName( )) ;
							cfp . setContent( mot ) ;
							cfp . setConversationId("traducteur");
							System.out.println("sending request");
							cfp.setReplyWith( "cfp "+System.currentTimeMillis( ) ) ;
							//envoyer le message
							myAgent.send( cfp);
							mt = MessageTemplate.and(MessageTemplate.
									MatchConversationId( "traducteur" ) ,MessageTemplate .
									MatchInReplyTo(cfp.getReplyWith()));
						}
					}
							
			}
		});
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage reponse = myAgent.receive(mt);
				if(reponse !=null){
					if(reponse.getPerformative()==ACLMessage.INFORM) {
						rep=(String)reponse.getContent().trim();
						Gui.updateTextArea(rep,reponse.getSender().getLocalName());
						System.out.println("response receiveddddddd "+rep);
						}
					if(reponse.getPerformative()==ACLMessage.REFUSE){
						rep=(String)reponse.getContent().trim();
						Gui.updateTextArea(rep,this.getAgent().getLocalName());
						System.out.println("response receiveddddddd mot inconnue ");
					}
				}
				else block();
				
			}
		});
		System.out.println("le mot rechercher est :"+mott);
	}
	protected void takeDown(){
		System.out.println("Agent"+getAID().getName()+" quitte la plateform.");
	}
	

}
