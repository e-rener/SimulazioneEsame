package it.polito.tdp.seriea.model;

public abstract class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model model = new Model();
		Season s = new Season(2003, "2002/2003");
		System.out.println(model.createGraph(s));
		System.out.println(model.createGraph(s).edgeSet());

	}

}
