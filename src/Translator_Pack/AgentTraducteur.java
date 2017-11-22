package Translator_Pack;
import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import Graphic.TranslatorGui;

import java.util.Hashtable;


public class AgentTraducteur extends Agent{
	private Hashtable catalogue;
	private TranslatorGui myGui;
	
	public void setup(){
		catalogue=new Hashtable();
		myGui = new TranslatorGui( this ) ;
		myGui.affiche( ) ;
		DFAgentDescription dfd = new DFAgentDescription( ) ;
		dfd.setName(getAID( )) ;
		ServiceDescription sd = new ServiceDescription( );
		sd.setType("traducteur" );
		sd.setName( "traducteur" ) ;
		dfd .addServices( sd ) ;
		try { DFService.register( this , dfd ) ; }
		catch ( FIPAException fe ) { fe.printStackTrace() ; }
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				MessageTemplate mt =MessageTemplate. MatchPerformative(ACLMessage.CFP);
				ACLMessage msg = myAgent.receive( mt ) ;
				if ( msg != null ) {
					String mot = msg .getContent();
					ACLMessage reponse = msg.createReply( ) ;
					String restext =(String) catalogue.get(mot) ;
					if (restext !=null) {
						// Le L i v r e e s t d i s p o n i b l e . Ré p o n d r e a v e c l e p r i x
						reponse. setPerformative( ACLMessage.INFORM);
						reponse.setContent(restext) ;
						reponse.setConversationId("traducteur");
						myAgent.send(reponse);
						System.out.println("sendinnnnnng response "+restext);
					}
					else {
						// l i v r e non d i s p o n i b l e à l a v e n t e , r é p o n d r e un r e f u s
						reponse .setPerformative(ACLMessage . REFUSE ) ;
						reponse.setContent("indisponible") ;
					}
					myAgent.send(reponse);
					msg=null;
				}
				else block () ;
				//attente d’ un message
				
			}

		});
	}
	public void updateCatalogue (final String eng ,final String alm ) {
		addBehaviour( new OneShotBehaviour( ) {
		public void action () {
		catalogue.put(eng,alm);
		System.out. println("le mot :"+eng+" est inseré au catalogue. almand= "+alm ) ;
		} } );
	}
	public void tkeDown(){
		System.out.print("agent quitte la plateform : "+getAID().getName());
	}

}
