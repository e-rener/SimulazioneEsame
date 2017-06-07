package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	SerieADAO dao;
	DirectedGraph<Team, Match> graph;
	Map<String, Team> teams;
	List<Team> listTeams;
	
	public Model(){
		dao = new SerieADAO();
		teams = new HashMap<String, Team>();
		listTeams =new ArrayList<Team>();
	}

	public List<Season> getSeasons() {
		return dao.listSeasons();
	}

	public DirectedGraph<Team, Match> createGraph(Season season) {
		if(graph != null){
			return graph;
		}
		else {
			graph = new SimpleDirectedWeightedGraph<Team, Match>(Match.class);
			Graphs.addAllVertices(graph, dao.getTeams(season));
			listTeams.addAll(dao.getTeams(season));
			for(Team t: dao.getTeams(season)){
				teams.put(t.getTeam(),t);
				t.azzeraPunteggio();
			}
			
			for(Match m: dao.listMatches(season)){
				Team teamH =teams.get(m.getHomeTeam().getTeam());
				Team teamA = teams.get(m.getAwayTeam().getTeam());
				if(teamH == null) System.out.println("Team non trovato model:49");
				if(teamA == null) System.out.println("Team non trovato model:49");
				
				graph.addEdge(teamH, teamA);
				switch(m.getFtr()){
				case 1: teamH.incrementaPunteggio(3);
				break;
				case 0: teamH.incrementaPunteggio(1);
				break;
				case -1: teamH.incrementaPunteggio(0);
				break;
				default : System.out.println("ftr non riconosciuto"); break;
				}
			}
			
			return graph;
		}
	}

	public List<Team> getTeams() {
		return listTeams;
	}
	
	

}
