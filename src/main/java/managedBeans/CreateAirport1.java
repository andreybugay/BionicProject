package managedBeans;

import iservice.IAirportService;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;

import entities.Airport;

@Named("airport1")
@Scope("session")
public class CreateAirport1 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	IAirportService as;

	@ManagedProperty(value="#{city}")
	private String city;
	@ManagedProperty(value="#{country}")
	private String country;
	@ManagedProperty(value="#{name}")
	private String name;
	
	private final String cityRequired = "Input a city";
	private final String countryRequired = "Input a country";
	private final String nameRequired = "Input your the name";
	
	public void checkRole() throws IOException  {
		HttpSession session = Util.getSession();
		String position = (String) session.getAttribute("position");		
		if(position == null || !position.equals("Booking office administrator")) {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			System.out.println("User with " + position + " tried to get reach Admin page (create Airport)");

			context.redirect("signIn.xhtml");
		}
	}
	
	public String submit() throws IOException {
		Airport a =new Airport();
		a.setCity(city);
		a.setCountry(country);
		a.setName(name);
		as.persist(a);
		clearFields();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "The new airport has been successfully added"));
		return "createAirport";

	}

	private void clearFields() {
		city = "";
		country = "";
		name = "";
	}

	public String backToList() {
		return "findAirports";
	}


	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityRequired() {
		return cityRequired;
	}
	public String getCountryRequired() {
		return countryRequired;
	}
	public String getNameRequired() {
		return nameRequired;
	}
}
