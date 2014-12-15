package br.com.lelak.teste.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lelak.teste.model.EntityBean;

@FacesConverter(value = "EntityConverter")
public class EntityConverter implements Converter {	

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {

		if (value != null && !value.trim().isEmpty()) {
			return getAttributesFrom(component).get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component, Object object) {

		if (object != null && !"".equals(object)) {
			EntityBean entity = (EntityBean) object;
			addAttribute(component, entity);

			Long codigo = entity.getId();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}
		return (String) object;
	}

	protected void addAttribute(UIComponent component, EntityBean o) {

		String key = o.getId().toString();
		getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {

		return component.getAttributes();
	}
}