package com.bitmonlab.batch.imports.map.managers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import com.bitmonlab.batch.imports.map.model.osm.Member;
import com.bitmonlab.batch.imports.map.model.osm.Relation;

public class UnMarshallerListener extends Unmarshaller.Listener {
	
	//This method only allows two parameters!!!
	public void afterUnmarshal(Object target, Object parent) {
			
		if (target instanceof Relation){		
				//System.out.println(((Relation)target).getId());
				outerBeforeInner((Relation) target); //Workaround for MongoDB 2.6.x
		}				
			
	}
	
	
	private void outerBeforeInner(Relation relation){
		
		List<Member> inners = new ArrayList<Member>();
		List<Member> outers = new ArrayList<Member>();
		
		
		if(relation.getMembers()!=null){
			
			for(Member member : relation.getMembers()){
				
				if(member.getRole().equals("inner")){
					inners.add(member);				
				}else if(member.getRole().equals("outer")){			
					outers.add(member);								
				}
				
			}
			
			if(!outers.isEmpty() && !inners.isEmpty()){
				relation.getMembers().removeAll(outers);
				relation.getMembers().removeAll(inners);
				relation.getMembers().addAll(relation.getMembers().size(), outers);
				relation.getMembers().addAll(relation.getMembers().size(), inners);
			}
		}	
		
	}	
					

}
